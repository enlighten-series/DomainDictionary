import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

@Injectable()
export class HttpStatusInterceptor implements HttpInterceptor {

  constructor(
    private router: Router,
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .do(event => {
        // noop
      })
      .catch(error => {
        if (error instanceof HttpErrorResponse) {
          if (error.status == 404) {
            this.router.navigate(['/404'], {skipLocationChange: true});
          }
        }
        return Observable.throw(error);
      });
  }

}
