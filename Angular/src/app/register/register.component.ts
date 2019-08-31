import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserDetail } from '../classes/user-detail';
import { UserDetailService } from '../services/user-detail.service';
import { jsonpFactory } from '@angular/http/src/http_module';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  selectedFiles: FileList;
  currentFileUpload: File;

  private userDetail = new UserDetail(); 
  
  constructor(private userDetailService : UserDetailService) { }

  ngOnInit() {
  }

  selectFile(event) {
    const file = event.target.files.item(0);
 
    if (file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }   

  // create the form object.
  form = new FormGroup({
    fullName : new FormControl('' , Validators.required),
    email : new FormControl('' , Validators.required),
    profileImage : new FormControl()
  });

  AdminForm(AdminInformation)
  {
    this.userDetail.name = this.FullName.value;
    this.userDetail.emailId = this.Email.value;

    console.log(this.userDetail);

    this.userDetailService.saveData(this.userDetail).subscribe(
      response => {
          let result = response.json();
          console.log(result);
          if(result > 0 )
          {
            if(this.selectedFiles != null)
            {
              this.currentFileUpload = this.selectedFiles.item(0);
              console.log(this.currentFileUpload);

              this.userDetailService.uploadFile(this.currentFileUpload , result).subscribe(
                  res => {

                    let re = res.json();
                     if(re > 0)
                     {
                        alert("file upload successfully ");
                        this.form.get('fullName').setValue("");
                        this.form.get('email').setValue("");
                        this.form.get('profileImage').setValue("");
                     }
                     else{
                        alert("error while uploading fie details");
                     }
                  },
                  err => {
                      alert("error while uploading fie details");
                  }
              );

            }
          }
      },
      error => {
        console.log("error while saving data in the DB");
      }
    );

  }

    get FullName(){
      return this.form.get('fullName');
    }

    get Email(){
        return this.form.get('email');
    } 




     
}