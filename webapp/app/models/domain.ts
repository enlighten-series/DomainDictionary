export class Domain {
  id: number;
  name: string;
  format: string;
  description: string;
  existential: string;

  constructor(
    id?,
    name?,
    format?,
    description?,
    existential?,
  ) {
    this.id = id ? id : 1;
    this.name = name ? name : '';
    this.format = format ? format : '';
    this.description = description ? description : '';
    this.existential = existential ? existential : '';
  }
}