import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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

  dataChange: BehaviorSubject<Domain[]> = new BehaviorSubject<Domain[]>([]);

  constructor(
    private router: Router,
    private http: HttpClient,
  ) { }

  ngOnInit() {
    this.displayedColumns = [
      'name',
      'format',
      'description',
      'edit',
    ];

    this.domainSource = new ExampleDataSource(this.dataChange);

    this.filterNameSubscription = Observable.fromEvent(this.filterName.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        this.domainSource.nameFilter = this.filterName.nativeElement.value;
      });
    this.filterDescriptionSubscription = Observable.fromEvent(this.filterDescription.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        this.domainSource.descriptionFilter = this.filterDescription.nativeElement.value;
      });

    this.http.get('/api/domains')
      .subscribe(
        (data: any) => {
          this.dataChange.next(data);
        },
        (error) => {
          console.log(error);
          this.dataChange.next([]);
        }
      );
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

class ExampleDataSource extends DataSource<Domain> {
  _nameFilterChange = new BehaviorSubject('');
  set nameFilter(filter: string) {
    this._nameFilterChange.next(filter);
  }
  get nameFilter() {
    return this._nameFilterChange.value;
  }
  _descriptionFilterChange = new BehaviorSubject('');
  set descriptionFilter(filter: string) {
    this._descriptionFilterChange.next(filter);
  }
  get descriptionFilter() {
    return this._descriptionFilterChange.value;
  }

  constructor(
    private dataChange: BehaviorSubject<Domain[]>
  ) {
    super();
  }

  connect(): Observable<Domain[]> {
    const displayDataChanges = [
      this.dataChange,
      this._nameFilterChange,
      this._descriptionFilterChange,
    ];

    return Observable.merge(...displayDataChanges).map(() => {
      return this.dataChange.value.slice().filter((item: Domain) => {
        let through = true;
        // 項目名フィルタ適用
        if (item.name.indexOf(this.nameFilter) == -1) {
          through = false;
        }
        // 説明フィルタ適用
        if (item.description && item.description.indexOf(this.descriptionFilter) == -1) {
          through = false;
        }
        return through;
      });
    });
  }

  disconnect() {}
}
