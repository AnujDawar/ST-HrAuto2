export class EducationFacilityApproval
{
    public     employeeName:                   string;
    public     employeeId:                     string;
    public     dateOfJoining:                  string;
    public     mobile:                         string;
    public     department:                     string;
    public     designation:                    string;
    public     nameOfInstitution:              string;
    public     programTitle:                   string;
    public     estimatedDurationOfCourse:      number;
    public     dateOfJoiningOfCourse:          string;
    public     totalCost:                      number;
    public     costOfProgram:                  number;
    public     hrResponsible:                  string;
    public     approvedAmount:                 number;
    public     howProgrmContributes:           string;
    public     isPartOfDevelopmentPlan:        boolean;
    public     availedPastCourseName:          string;
    public     availedPastCouseStartDate:      string;
    public     availedPastCouseEndDate:        string;
    public     isAgree:                        boolean;
    public     attachments:                    [];
    public     requestId:                      number;
    public     attachmentIdsToRemove:          string;

    constructor()
    {
        this.attachments = [];
    }
}