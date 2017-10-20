export class Domain {
  name: string;
  type: string;
  length: number;
  format: string;
  description: string;

  constructor(
    name,
    type,
    length,
    format,
    description,
  ) {
    this.name = name;
    this.type = type;
    this.length = length;
    this.format = format;
    this.description = description;
  }
}