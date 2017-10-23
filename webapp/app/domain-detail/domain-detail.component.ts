import { Component, OnInit } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
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

  constructor(
    private activateRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.activateRoute.paramMap
    .switchMap((param: ParamMap) => {
      console.log('routed:' + param.get('id'));
      return this.getDomain(param.get('id'));
    })
    .first()
    .subscribe((data) => {
      console.log('subscribe!');
      console.log(data);
    });
  }

  getDomain(id): Observable<Domain> {
    return Observable.from(data)
    .filter((data) => {
      if(data.id == id) {
        return true;
      }
    });
  }

}

const data: Domain[] = [
  new Domain(
    1,
    '契約番号',
    '半角英数',
    10,
    '9-999-99999',
    '顧客ごとに自動的に付与され…',
  ),
  new Domain(
    2,
    '顧客正式名称',
    '全角文字列',
    50,
    'ー',
    'ー',
  ),
];

export class ExampleDataSource extends DataSource<Domain> {
  connect(): Observable<Domain[]> {
    return Observable.of(data);
  }

  disconnect() {}
}
