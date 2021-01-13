import {GP} from './GP';

export class User {
    firstName: string;
    lastName: string;
    middleName: string;
    email: string;
    phoneNumber: string;
    mobileNumber: string;
    password: string;
    gp: GP;

    constructor(firstName: string = null, lastName: string = null, middleName: string = null, email: string = null,
                phoneNumber: string = null, mobileNumber: string = null, password: string = null, gp: GP = null) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.gp = gp;
    }
}
