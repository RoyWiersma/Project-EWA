import {User} from './User';

export class Patient extends User {

    id: number;

    constructor(id: number = null, firstName: string = null, lastName: string = null, middleName: string = null, email: string = null, phoneNumber: string = null, mobileNumber: string = null) {
        super(firstName, lastName, middleName, email, phoneNumber, mobileNumber);

        this.id = id;
    }
}
