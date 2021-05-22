import { Component, OnInit } from '@angular/core';
import {Router, NavigationExtras} from "@angular/router";

/* Login Authentication Library Import */
import { SocialAuthService } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';  /* Import for storing User Information */
import {
  GoogleLoginProvider
} from 'angularx-social-login';
import { ServiceClassService } from '../service-class.service';

/* Login Authentication Library Import */

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  // user: SocialUser;

  constructor(private authService: SocialAuthService, private router:Router, private swService : ServiceClassService) { }

  ngOnInit() {

  }

  signInWithGoogle(): void {
    this.swService.signInWithGoogle();
    // this.authService.authState.subscribe(user => {
    //   if(user)
    //   {
    //     console.log(this.authService);
    //     this.router.navigate(["/home"]);
    //   }
    //   this.user = user;

    //   /* Use to Store the details in Local Storage */
    //   this.swService.setSettings(this.user);
    //   console.log(this.user);
    // });
    // this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signOut(): void {
    // this.authService.signOut(true);
  }

  /*
  // Refresh Token Value

  refreshGoogleToken(): void {
    this.authService.refreshAuthToken(GoogleLoginProvider.PROVIDER_ID);
  }
 */

}
