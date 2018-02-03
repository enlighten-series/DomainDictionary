import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data } from '@angular/router';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  message$: BehaviorSubject<String>;

  constructor(
    private activateRoute: ActivatedRoute,
  ) { }
  
  ngOnInit() {
    this.message$ = new BehaviorSubject('');

    this.activateRoute.data
      .first()
      .subscribe((data: Data) => {
        if (data.status === '404') {
          this.message$.next('404 Not Found');
        }
      });
  }

}
