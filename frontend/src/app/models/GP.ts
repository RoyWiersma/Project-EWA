import {User} from './User';

export class GP extends User {

    isAdmin: boolean;
    speciality: string;

    constructor(
        firstName: string, lastName: string, middleName: string, email: string, phoneNumber: string, mobileNumber: string,
        isAdmin: boolean, speciality: string
    ) {
        super(firstName, lastName, middleName, email, phoneNumber, mobileNumber);
        this.isAdmin = isAdmin;
        this.speciality = speciality;
    }
}
