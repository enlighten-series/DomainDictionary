import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/Subscription';

import 'rxjs/add/observable/from';
import 'rxjs/add/operator/first';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/switchMap';

import { Domain } from '../models/domain';

@Component({
  selector: 'app-domain-detail',
  templateUrl: './domain-detail.component.html',
  styleUrls: ['./domain-detail.component.css']
})
export class DomainDetailComponent implements OnInit {

  id: number;
  activeIndex = 0;

  viewDomain = {};

  constructor(
    private activateRoute: ActivatedRoute,
    private http: HttpClient,
  ) { }

  ngOnInit() {
    this.activateRoute.paramMap
    .first()
    .subscribe((param: ParamMap) => {
      this.id = Number(param.get('id'));
      this.http.get('/api/domains/' + param.get('id'))
      .subscribe(
        (domain: any) => {
          this.viewDomain = domain;
        },
        (error) => {
          console.log(error);
        }
      );
    });
  }

  selectedIndexChanged(index) {
    this.activeIndex = index;
  }
  isActiveDetail() {
    return this.activeIndex == 0;
  }
  isActiveEdit() {
    return this.activeIndex == 1;
  }
  isActiveOther() {
    return this.activeIndex == 2;
  }

  emittedRegist() {
    console.log('DomainDetailComponent regist emiited!');
  }

  clickedDelete() {
    console.log('delete:' + this.id);
  }

}
