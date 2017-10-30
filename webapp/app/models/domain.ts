export class Domain {
  id: number;
  name: string;
  format: string;
  description: string;
  existential: string;

  constructor(
    id,
    name,
    format,
    description,
    existential,
  ) {
    this.id = id;
    this.name = name;
    this.format = format;
    this.description = description;
    this.existential = existential;
  }
}