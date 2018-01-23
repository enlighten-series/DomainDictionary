import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { AuthService } from './auth/auth.service';
import { EmptyResponseBodyInterceptor } from './interceptor/empty-response-body-interceptor';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: EmptyResponseBodyInterceptor,
      multi: true,
    },
  ],
})
export class CoreModule { }
