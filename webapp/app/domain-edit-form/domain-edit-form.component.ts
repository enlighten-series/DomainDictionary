import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-domain-edit-form',
  templateUrl: './domain-edit-form.component.html',
  styleUrls: ['./domain-edit-form.component.css']
})
export class DomainEditFormComponent implements OnInit {

  @Output() regist = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  clickedRegist() {
    this.regist.emit();
  }

}
