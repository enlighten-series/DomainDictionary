export class Domain {
  id: number;
  name: string;
  format: string;
  description: string;
  existential: string;
  created: Date;
  updated: Date;

  constructor(
    id?,
    name?,
    format?,
    description?,
    existential?,
    created?,
    updated?,
  ) {
    this.id = id ? id : 1;
    this.name = name ? name : '';
    this.format = format ? format : '';
    this.description = description ? description : '';
    this.existential = existential ? existential : '';
    this.created = created ? created : new Date();
    this.updated = updated ? updated : new Date();
  }
}