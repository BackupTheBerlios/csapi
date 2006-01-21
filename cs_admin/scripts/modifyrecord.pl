#! /usr/bin/perl -w -I/home/grandpas/cs/lib
# replace with your installation settings
require 5.002;

use strict;
use Socket;
use Carp;
use FileHandle;
use Getopt::Long;
use ChangeSynergy::csapi;

# Usage: modifyrecord.pl [-h|help] [-i|identifie identifier] attribute value
# This script is gpl'ed. See www.fsf.org.

my $usage = <<EOU;
usage: $0 [-h|help] [-i|identifier identifier] attribute value

$0 allows a Change Synergy administrator to set attributes 
for problem_number furnished either on command line (with
the -i switch) or on stdin.

\$ $0 -i 567 problem_synopsis "User should be able to change theme."

EOU
die $usage if ($#ARGV == -1);

Getopt::Long::Configure("bundling");

# Set accordingly to your installation
my $SERV_IP = "192.168.230.128";
my $SERV_DB = "/db/ccmdb/test_db";
my $USER_LOGIN = "grandpas";
my $USER_PASSWD = "xxxxxxxx";
my $USER_ROLE = "User";

# variable declaration
my ($record, $identifier, $queryargs, $user, 
    $opt_help, $opt_id);

my (@identifiers, $attribute,
    $old_value, $new_value);

# Define available options
my $options = GetOptions( 'i|identifier=i' => \$opt_id,
  'h|help' => \$opt_help);

# Display usage if help is needed.
die $usage if (defined($opt_help));

# If no id is furnished on command line, get from 
# stdin, one id per line.
if (!defined($opt_id)) {
    while (<STDIN>) {
	chomp;
	push @identifiers, $_;
    }
} else { push @identifiers, $opt_id; }

# Get parameters.
$attribute = shift;
$new_value = shift;

my $csapi = new ChangeSynergy::csapi();

eval
{
    $csapi->setUpConnection("http", $SERV_IP, 8600);
    $user = $csapi->Login($USER_LOGIN, $USER_PASSWD, 
			  $USER_ROLE, $SERV_DB);
    
    foreach $identifier (@identifiers) {
	# Request an object representing the record.
	$record = $csapi->GetCRData($user, $identifier, $attribute);
	
	# Request the "gravity" and "weight" attributes objects
	$old_value = $record->getDataObjectByName($attribute)->getValue();

	print "Changed $identifier from [$old_value] to [$new_value] \n";

	# assign result in weight attribute of the record
	$record->getDataObjectByName($attribute)->setValue($new_value);

	# apply changes
	$csapi->ModifyCR($user, $record);
    }
};

if ($@)
{
    print $@;
}


__END__

=pod

=head1 Name

B<modifyrecord.pl> -- Change the value of an attribute for a set of records.

=head1 Synopsis

Usage: B<bugvalcs> [-h|help] [-i|identifier identifier] attribute value

=head1 Description

The script uses an array to store problem_number's. It either push id furnished on command line or read and push from standard input. 

For each element of the array, it retrieves an object (getCRData), read (getValue) and set (setValue) the value of the attribute specified on command line. A line is shown with old and new values, along with corresponding problem_number.

=cut
