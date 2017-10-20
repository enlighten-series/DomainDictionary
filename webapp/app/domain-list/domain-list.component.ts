import { Component, OnInit } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

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

const data = [
  {
    name: '契約番号',
    type: '半角英数',
    length: '10',
    format: '9-999-99999',
    description: '顧客ごとに自動的に付与され…',
  },
  {
    name: '顧客正式名称',
    type: '全角文字列',
    length: '50',
    format: 'ー',
    description: 'ー',
  },
];

/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class ExampleDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<any[]> {
    return Observable.of(data);
  }

  disconnect() {}
}
