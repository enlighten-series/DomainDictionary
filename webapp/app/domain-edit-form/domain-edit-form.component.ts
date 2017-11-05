import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs/Observable';

import { Domain } from '../models/domain';

@Component({
  selector: 'app-domain-edit-form',
  templateUrl: './domain-edit-form.component.html',
  styleUrls: ['./domain-edit-form.component.css']
})
export class DomainEditFormComponent implements OnInit {

  @Output() regist = new EventEmitter();

  editingDomain: any = {
    name: '',
    format: '',
    description: '',
    existential: '',
  };

  constructor() { }

  ngOnInit() {
  }

  clickedRegist(form: NgForm) {
    if (!form.valid) {
      // 入力項目をdirtyにしてエラーメッセージを表示する
      Observable.from(Object.keys(form.controls))
      .subscribe(
        key => {
          form.controls[key].markAsDirty();
        }
      )
      return;
    }

    console.log(this.editingDomain);
    this.regist.emit(this.editingDomain);
  }

}
