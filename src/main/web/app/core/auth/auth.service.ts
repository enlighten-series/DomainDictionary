import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Auth } from './auth';

@Injectable()
export class AuthService {
  private auth$: BehaviorSubject<Auth> = new BehaviorSubject(<Auth>{});

  constructor(private http: HttpClient) {}

  updateAuthentication() {
    this.http.get('/api/authentication').subscribe(data => {
      this.auth$.next(<Auth>data);
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
