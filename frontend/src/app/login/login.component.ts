import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router } from '@angular/router'
import { AppGlobals } from '../global/global-config'
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router, 
    public appGlobals: AppGlobals, private fb: FormBuilder) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }
//Updation of login details
  onSubmit(): void {
    console.log('in onsubmit')
    let loginData = this.formBuilder();
    this.authService.login(loginData.value).subscribe({
      next: (res) => {
        console.log('next-------',res);
        if(res) {
          this.appGlobals.loginUserDetail = res;
          this.isLoggedIn = true;
          this.router.navigate(['/menu']);
        } else {
          this.isLoginFailed = true;
        }
      },
      error: (err) => {
        console.log("err-----", err)
        if(err.error.message) {
          this.errorMessage = err.error.message
        } else {
          this.errorMessage = err.error.errorDefinition.message;
        }
      },
      complete: () =>{

      }
    })
  }

  formBuilder() {
    const { username, password } = this.form;
    return (this.fb.group({
        userName: username,            
        password: password
    }));
  }

  reloadPage(): void {
    window.location.reload();
  }
}
