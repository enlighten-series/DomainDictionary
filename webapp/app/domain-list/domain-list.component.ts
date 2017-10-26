import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Subscription } from 'rxjs/Subscription';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/map';

import { Domain } from '../models/domain';

@Component({
  selector: 'app-domain-list',
  templateUrl: './domain-list.component.html',
  styleUrls: ['./domain-list.component.css']
})
export class DomainListComponent implements OnInit {

  @ViewChild('filterName') filterName;
  @ViewChild('filterDescription') filterDescription;
  private filterNameSubscription: Subscription;
  private filterDescriptionSubscription: Subscription;

  public displayedColumns: string[];
  public domainSource: ExampleDataSource;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
    this.displayedColumns = [
      'name',
      'type',
      'length',
      'format',
      'description',
      'edit',
    ];

    let exampleDatabase = new ExampleDatabase();
    this.domainSource = new ExampleDataSource(exampleDatabase);

    // フィルタ変更ストリーム
    this.filterNameSubscription = Observable.fromEvent(this.filterName.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        console.log('filter name:' + this.filterName.nativeElement.value);
        this.domainSource.filter = this.filterName.nativeElement.value;
      });
    this.filterDescriptionSubscription = Observable.fromEvent(this.filterDescription.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        console.log('filter description:' + this.filterDescription.nativeElement.value);
      });
  }
  
  create() {
    this.router.navigate(['/create']);
  }

  detail(id) {
    this.router.navigate(['/detail', id]);
  }

  ngOnDestroy() {
    this.filterNameSubscription.unsubscribe();
    this.filterDescriptionSubscription.unsubscribe();
  }

}

/*
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
*/

export class ExampleDatabase {
  dataChange: BehaviorSubject<Domain[]> = new BehaviorSubject<Domain[]>([]);
  get data(): Domain[] {
    return this.dataChange.value;
  }
  
  dataAsset: Domain[] = [
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
  
  constructor() {
    this.dataChange.next(this.dataAsset);
  }
}

export class ExampleDataSource extends DataSource<Domain> {
  _filterChange = new BehaviorSubject('');
  set filter(filter: string) {
    this._filterChange.next(filter);
  }
  get filter() {
    return this._filterChange.value;
  }

  constructor(
    private _exampleDatabase: ExampleDatabase
  ) {
    super();
  }

  connect(): Observable<Domain[]> {
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._filterChange,
    ];

    return Observable.merge(...displayDataChanges).map(() => {
      return this._exampleDatabase.data.slice().filter((item: Domain) => {
        let searchStr = item.name;
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });
    });
  }

  disconnect() {}
}
