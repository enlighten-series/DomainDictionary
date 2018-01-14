import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Subscription } from 'rxjs/Subscription';

import { fromEvent } from 'rxjs/observable/fromEvent';
import { merge } from 'rxjs/observable/merge';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

import { Domain } from '../models/domain';

@Component({
  selector: 'app-domain-list',
  templateUrl: './domain-list.component.html',
  styleUrls: ['./domain-list.component.css']
})
export class DomainListComponent implements OnInit {

  // #region interfaces

  // #endregion

  // #region constructor and lifecycle events

  constructor(
    private router: Router,
    private http: HttpClient,
  ) {
    this.displayedColumns = [
      'name',
      'format',
      'description',
      'edit',
    ];
    this.filteredDomains = new ViewDomainListDataSource(this.allDomains$);
  }

  ngOnInit() {
    this.setupSubscriptions();
    this.reqGetAllDomains();
  }
  
  ngOnDestroy() {
    this.destroySubscriptions();
  }

  // #endregion

  // #region view binds

  @ViewChild('filterName') filterName;
  @ViewChild('filterDescription') filterDescription;

  public displayedColumns: string[];
  public filteredDomains: ViewDomainListDataSource;

  // #endregion

  // #region view events

  create() {
    this.router.navigate(['/create']);
  }

  detail(id) {
    this.router.navigate(['/detail', id]);
  }

  // #endregion

  // #region privates

  private subscriptions: Subscription[] = [];
  private allDomains$: BehaviorSubject<Domain[]> = new BehaviorSubject<Domain[]>([]);

  private setupSubscriptions() {
    this.subscriptions.push(
      fromEvent(this.filterName.nativeElement, 'keyup').pipe(
        debounceTime(150),
        distinctUntilChanged()
      ).subscribe(() => {
        this.filteredDomains.nameFilter = this.filterName.nativeElement.value;
      })
    );
    this.subscriptions.push(
      fromEvent(this.filterDescription.nativeElement, 'keyup').pipe(
        debounceTime(150),
        distinctUntilChanged()
      ).subscribe(() => {
        this.filteredDomains.descriptionFilter = this.filterDescription.nativeElement.value;
      })
    );
  }

  private destroySubscriptions() {
    this.subscriptions.forEach(s => {
      s.unsubscribe();
    });
  }

  private reqGetAllDomains() {
    this.http.get('/api/domains')
      .subscribe(
        (data: any) => {
          this.allDomains$.next(data);
        },
        (error) => {
          console.log(error);
          this.allDomains$.next([]);
        }
      );
  }

  // #endregion

}

class ViewDomainListDataSource extends DataSource<Domain> {
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
    const listChangeCauses$ = [
      this.dataChange,
      this._nameFilterChange,
      this._descriptionFilterChange,
    ];

    return merge(...listChangeCauses$).pipe(
      map(() => this.dataChange.value.slice().filter(d =>  this.listFilter(d))),
    );
  }

  private listFilter(item: Domain): boolean {
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
  }

  disconnect() {}
}
