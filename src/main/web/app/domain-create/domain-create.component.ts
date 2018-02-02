import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';

import { GrowlMessagerComponent } from '../shared/widgets/growl-messager.component';

@Component({
  selector: 'app-domain-create',
  templateUrl: './domain-create.component.html',
  styleUrls: ['./domain-create.component.css']
})
export class DomainCreateComponent implements OnInit {

  constructor(
    private router: Router,
    private http: HttpClient,
    public snack: MatSnackBar,
  ) { }

  ngOnInit() {
  }

  emittedRegist(event) {
    this.http.post('/api/domains', event)
    .subscribe(
      (data: any) => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: '新しいドメインを登録しました',
          },
          duration: 1500,
        });
        this.router.navigate(['/']);
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
