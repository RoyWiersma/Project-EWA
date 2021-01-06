import {User} from './User';

export class GP extends User {

    isAdmin: boolean;
    speciality: string;
    id: number;

    constructor(
        id: number = null, firstName: string = null, lastName: string = null, middleName: string = null,
        email: string = null, phoneNumber: string = null, mobileNumber: string = null,
        isAdmin: boolean = null, speciality: string = null
    ) {
        super(firstName, lastName, middleName, email, phoneNumber, mobileNumber);
        this.isAdmin = isAdmin;
        this.speciality = speciality;
        this.id = id;
    }


}
