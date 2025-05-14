/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public enum Status {
    CREATION,                //initial state for package
    COLLECTION,              //when truck is on its way to pick up the package
    BRANCH_STORAGE,          //components.Package arrived sender's local branch
    HUB_TRANSPORT,           //components.Package transported to the sorting facility
    HUB_STORAGE,             //components.Package arrived to sorting facility
    BRANCH_TRANSPORT,        //components.Package on her way to the reciever's branch
    DELIVERY,                //The package has arrived to destination branch and ready for delivery to reciever
    DISTRIBUTION,            //The package is on its way from the destination branch to the final customer
    DELIVERED                //The package was delivered to the final customer
}
