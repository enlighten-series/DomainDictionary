import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-domain-create',
  templateUrl: './domain-create.component.html',
  styleUrls: ['./domain-create.component.css']
})
export class DomainCreateComponent implements OnInit {

  constructor(
    private router: Router,
  ) { }

  ngOnInit() {
  }

  emittedRegist(event) {
    console.log('DomainCreateComponent regist emitted!');
    console.log(event);

    this.router.navigate(['/']);
  }

}
