import { Component, OnInit } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { Domain } from '../models/domain';

@Component({
  selector: 'app-domain-list',
  templateUrl: './domain-list.component.html',
  styleUrls: ['./domain-list.component.css']
})
export class DomainListComponent implements OnInit {

  public displayedColumns: string[];
  public domainSource$ = new ExampleDataSource();

  constructor() { }

  ngOnInit() {
    this.displayedColumns = [
      'name',
      'type',
      'length',
      'format',
      'description',
    ];
  }

}

const data: Domain[] = [
  new Domain(
    '契約番号',
    '半角英数',
    10,
    '9-999-99999',
    '顧客ごとに自動的に付与され…',
  ),
  new Domain(
    '顧客正式名称',
    '全角文字列',
    50,
    'ー',
    'ー',
  ),
];


export class ExampleDataSource extends DataSource<any> {
  connect(): Observable<Domain[]> {
    return Observable.of(data);
  }

  disconnect() {}
}
