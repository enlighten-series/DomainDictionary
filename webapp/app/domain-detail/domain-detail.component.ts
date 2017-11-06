import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/Subscription';

import 'rxjs/add/observable/from';
import 'rxjs/add/operator/first';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/switchMap';

import { Domain } from '../models/domain';
import { GrowlMessagerComponent } from '../widgets/growl-messager.component';

@Component({
  selector: 'app-domain-detail',
  templateUrl: './domain-detail.component.html',
  styleUrls: ['./domain-detail.component.css']
})
export class DomainDetailComponent implements OnInit {

  id: number;
  activeIndex = 0;

  viewDomain = {};
  editFormInitialValue: Domain;

  constructor(
    private activateRoute: ActivatedRoute,
    private http: HttpClient,
    private snack: MatSnackBar,
  ) { }

  ngOnInit() {
    this.activateRoute.paramMap
    .first()
    .subscribe((param: ParamMap) => {
      this.id = Number(param.get('id'));
      this.load();
    });
  }

  load() {
    this.http.get('/api/domains/' + this.id)
    .subscribe(
      (domain: any) => {
        this.viewDomain = domain;
        this.editFormInitialValue = domain;
      },
      (error) => {
        console.log(error);
      }
    );
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

  emittedRegist(event) {
    this.http.put('/api/domains/' + this.id, event)
    .subscribe(
      (data: any) => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'ドメインを更新しました',
          },
          duration: 1500,
        });
        this.load();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  clickedDelete() {
    console.log('delete:' + this.id);
  }

}
