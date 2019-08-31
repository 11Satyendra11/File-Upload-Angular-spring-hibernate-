import { Injectable } from '@angular/core';
import { UserDetail } from '../classes/user-detail';
import { Observable } from 'rxjs';
import { Http, RequestOptions , Headers } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class UserDetailService {

  // Base URL
  private  baseUrl = "http://localhost:8080/FileUploadExample/api/";

  constructor(private http: Http) { }

  saveData(userDetail : UserDetail) : Observable<any>
  {
      let url = this.baseUrl + "saveUser";
      return this.http.post(url,userDetail);
  }

  uploadFile( file: File , id : number ) : Observable<any>
  {
    let url = this.baseUrl + "uploadImage/" + id ;

    const formdata: FormData = new FormData();
  
    formdata.append('file', file);
 
    return this.http.post(url , formdata);
  }
}
