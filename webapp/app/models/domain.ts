export class Domain {
  id: number;
  name: string;
  type: string;
  length: number;
  format: string;
  description: string;

  constructor(
    id,
    name,
    type,
    length,
    format,
    description,
  ) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.length = length;
    this.format = format;
    this.description = description;
  }
}