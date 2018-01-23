import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Auth } from './auth';

@Injectable()
export class AuthService {

  private auth$: BehaviorSubject<Auth> = new BehaviorSubject(<Auth>{});

  constructor() { }

  updateAuthentication() {
    this.auth$.next(<Auth>{
      username: 'admin'
    });
  }

  clearAuthentication() {
    this.auth$.next(<Auth>{});
  }

  getAuthData(): Auth {
    return this.auth$.value;
  }

  isAuthenticated(): boolean {
    if (this.auth$.value.username) {
      return true;
    }
    return false;
  }

}
