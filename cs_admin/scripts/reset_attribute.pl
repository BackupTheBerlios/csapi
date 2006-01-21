# <trigger>
#   <usage>perl reset_attribute.pl</usage>
#   <version>4.3_sp2</version>
#   <description>reset_attribute.pl should be set as a post-condition triggers
#   on an attribute. It checks for its value and set it back if changed, under
#   some conditions.
#   </description>
# </trigger>

# If the project_name is "Product A" the script changes the project_name value to "Project B"

require 5.002;
use strict;

use Socket;
use Carp;
use FileHandle;

# Used to parse the trigger XML file sent from ChangeSynergy.
use ChangeSynergy::TriggerParser;

# Main module for the CS API.
use ChangeSynergy::csapi;


#################################################################
#                                                               #
# Subroutines and variables declaration                         #
#                                                               #
#################################################################

my ($uid);

sub miscellaneous_checks;


#################################################################
#                                                               #
# Core part of programm                                         #
#                                                               #
#################################################################


# Create the trigger, with xml doc as argument. 
# XML stuff is provided by the Change/Synergy engine.
my $trigger = new ChangeSynergy::TriggerParser($ARGV[-1]);

#######################################################
# Get the transition 
#######################################################

# Get the problem number from the trigger.
my $problem_number = $trigger->get_object_id();

# eval is used for exception catching
eval
{
    my $csapi = new ChangeSynergy::csapi();
    my ($problem,
	$user, 
	$crstatus,
	$gravity,
	$weight);
    

    # Define the connection with server access info.
    $csapi->setUpConnection($trigger->get_protocol(), 
			    $trigger->get_host(), 
			    $trigger->get_port());
    
    # Create a user object for login, password and role used for connection.
    $user = new ChangeSynergy::apiUser($trigger->get_user(), 
				       "", 
				       $trigger->get_role(),
				       $trigger->get_token(), 
				       $trigger->get_database());

    # Get attributes values for the problem_number who fired the trigger.
    $problem = $csapi->GetCRData($user, $problem_number, "crstatus|project_name");
    
    # Extract crstatus and project_name attributes.
    $crstatus = $problem->getDataObjectByName("crstatus")->getValue();
    $project_name = $problem->getDataObjectByName("project_name")->getValue();

    # Comment for silent execution.
    print "cr in [$crstatus] from [$project_name].. ";
    
    # If miscellanous_checks() returns false, the script does not change 
    # the value and therefore exits.
    my $check_result = miscellaneous_checks($crstatus, $project_name);
    if (!$check_result)
    {
	print "failed.\n";
	exit;
    }

    # Change attribute value to "Product B".
    $problem->getDataObjectByName("project_name")->setValue("Product B");

    # Apply modifications.
    $csapi->ModifyCR($user, $problem);

    print "changed to [$project_name]. \n";
};

# Catch exception.
if($@)
{
	print "Error on cr [" . $problem_number . "]: " . $@;
	exit 0;
}

#################################################################
#                                                               #
# sub miscellaneous_checks($crstatus, $project_name)            #
#                                                               #
# Do some checks and returns a boolean indicating if the cr     #
# should be changed or not.                                     #
#                                                               #
# Parameters are:                                               # 
#  * $crstatus is the state where the records currently stay    #
#     in.                                                       #
#  * $project_name is the attribute to check.                   #
#                                                               #
#################################################################

sub miscellaneous_checks {
    my $crstatus = shift;
    my $project_name = shift;

    if ($project_name = "Product A")
    {
	return true;
    }

    return false;
}
