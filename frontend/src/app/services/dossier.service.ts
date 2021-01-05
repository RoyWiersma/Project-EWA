import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class DossierService {

    constructor(private http: HttpClient) {
    }

    public getAllByPatient(id: number): Observable<any> {
        return this.http.get(`http://localhost:8085/gp/patient/${id}/dossier`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getMediaItem(id: number): Observable<any> {
        return this.http.get(`http://localhost:8085/dossier/medical-media/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public saveMediaItem(formData: FormData, patientId: number): Observable<any> {
        return this.http.post(`http://localhost:8085/gp/patient/${patientId}/dossier`, formData, {
            headers: {
                Accept: 'application/json',
                authorization: localStorage.getItem('jwt') || null
            }
        });
    }

    public deleteItemById(id: number): Observable<any> {
        return this.http.delete(`http://localhost:8085/dossier/medical-media/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }
}
