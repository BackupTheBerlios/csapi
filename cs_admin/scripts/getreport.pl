#! /usr/bin/perl -w -I/home/grandpas/cs/lib
# replace with your installation settings

use strict;
use Getopt::Long;
use ChangeSynergy::csapi;
use Time::gmtime;

# Usage: get_report.pl [-f format] query attributes
# This script is gpl'ed. See www.fsf.org.

my $usage = <<EOU;
usage: $0 [-h] [-f format ] [-q query] attributes

$0 displays a text report with specified attributes;

The records to retrieve are either specified on stdin
or directly specified with a query string. 

If a format string is provided (classic printf C 
format) attributes will follow String format.

$0 -f "[%s] %s|%s|%s" -q "(software match 'good*')" 
"problem_number|problem_synopsis|software_platfo
rm|software_version" 
[12990] search fails|embedded|2.3
[14851] generated html does not validate|server|2.1
[14928] dead links|server|2.1

EOU
die $usage if ($#ARGV == -1);

Getopt::Long::Configure("bundling");

# Set accordingly to your installation
my $SERV_IP = "192.168.230.128";
my $SERV_DB = "/db/ccmdb/test_db";
my $USER_LOGIN = "grandpas";
my $USER_PASSWD = "xxxxxxxx";
my $USER_ROLE = "User";

# Variables declaration
my ($user, $results,
    $queryargs, $querystring, 
    $opt_query, $opt_help, 
    $opt_format,
    $identifier, @identifiers);

# Define available options
my $options = GetOptions( 'q|query=s' => \$opt_query,
  'h|help' => \$opt_help, 'f|format:s' => \$opt_format);

die $usage if (defined($opt_help));

# If no query is furnished, build a query to retrieve 
# needed attributes from problem_number given as std 
# input.
if (defined($opt_query)) { $querystring = $opt_query; }
else {
  while (<STDIN>) {
    chomp;
    push @identifiers, $_;
  }
  $identifier = pop @identifiers;
  $querystring = qq/(problem_number='$identifier')/;
  foreach $identifier (@identifiers) {
    $querystring = $querystring . qq/or(problem_number='$identifier')/;
  }
}
    
eval
{
  $queryargs = join "|", @ARGV;

  my $csapi = new ChangeSynergy::csapi();
  $csapi->setUpConnection("http", $SERV_IP, 8600);
  $user = $csapi->Login($USER_LOGIN, $USER_PASSWD, 
                        $USER_ROLE, $SERV_DB);
  
  # Get query data
  $results = $csapi->QueryData($user, "Basic Summary", 
     $querystring, undef, undef, undef, $queryargs);
};

if ($@)
{
  #print $@;
  print "No match. Dying. \n";
  exit 64;
}  

# Loop through the results to display formatted attributes
for (my $i = 0 ; $i < $results->getDataSize() ; $i++) {
  my $report_data = $results->getDataObject($i);
  my @attribute_data;
  my $finalattr;


  for (my $j = 0 ; $j < $report_data->getDataSize() ; $j++) {
    # converts universal time since epoch to modern styles.
    if ($report_data->getDataObject($j)->getType() eq "CCM_DATE") {
      my $gm = gmtime($report_data->getDataObject($j)->getValue());
      $attribute_data[$j] = sprintf "%2.2d%2.2d%2.2d", 
          $gm->year-100, $gm->mon+1, $gm->mday+1;
    } else {
      $attribute_data[$j] = $report_data->getDataObject($j)->getValue();
    }
  }
  if (defined($opt_format)) {
    $finalattr = sprintf $opt_format, @attribute_data;
  } else {
    $finalattr = join "|", @attribute_data;
  }
  print "$finalattr \n";
}

__END__

=pod

=head1 Name

B<getreport.pl> -- Displays specified fields from 
a selection of problem reports.

=head1 Synopsis

Usage: B<getreport.pl> [-h] [-f format ] [-q query] attributes

=head1 Description

$0 displays a text report with specified attributes;

The records to retrieve are either read on stdin
or directly specified with a query string. 

If a format string is provided (classic printf C 
format) attributes will follow String format.

$0 -f "[%s] %s|%s|%s" -q "(software match 'good*')" 
"problem_number|problem_synopsis|software_platform|software_version" 
[12990] search fails|embedded|2.3
[14851] generated html does not validate|server|2.1
[14928] dead links|server|2.1

=cut

