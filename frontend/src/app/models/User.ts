export class User {
    firstName: string;
    lastName: string;
    middleName: string;
    email: string;
    phoneNumber: string;
    mobileNumber: string;

    constructor(firstName: string, lastName: string, middleName: string, email: string, phoneNumber: string, mobileNumber: string) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
    }
}
