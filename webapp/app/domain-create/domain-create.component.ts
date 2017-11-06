import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-domain-create',
  templateUrl: './domain-create.component.html',
  styleUrls: ['./domain-create.component.css']
})
export class DomainCreateComponent implements OnInit {

  constructor(
    private router: Router,
    private http: HttpClient,
  ) { }

  ngOnInit() {
  }

  emittedRegist(event) {
    this.http.post('/api/domains', event)
    .subscribe(
      (data: any) => {
        this.router.navigate(['/']);
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
