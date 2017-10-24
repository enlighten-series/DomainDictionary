import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-domain-edit-form',
  templateUrl: './domain-edit-form.component.html',
  styleUrls: ['./domain-edit-form.component.css']
})
export class DomainEditFormComponent implements OnInit {

  typeOptions = [
    {label: '数値', value: 'number'},
    {label: '混在文字列', value: 'free'},
  ]

  constructor() { }

  ngOnInit() {
  }

}
