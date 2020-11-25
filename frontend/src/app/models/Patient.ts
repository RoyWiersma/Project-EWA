import {User} from './User';

export class Patient extends User {

    constructor(firstName: string, lastName: string, middleName: string, email: string, phoneNumber: string, mobileNumber: string) {
        super(firstName, lastName, middleName, email, phoneNumber, mobileNumber);
    }
}
