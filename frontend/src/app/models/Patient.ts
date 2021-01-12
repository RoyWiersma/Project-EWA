import {User} from './User';
import {GP} from './GP';

export class Patient extends User {

    id: number;

    constructor(id: number = null, firstName: string = null, lastName: string = null, middleName: string = null,
                email: string = null, phoneNumber: string = null, mobileNumber: string = null, password: string = null, gp: GP = null) {
        super(firstName, lastName, middleName, email, phoneNumber, mobileNumber, password, gp);

        this.id = id;
    }
}
