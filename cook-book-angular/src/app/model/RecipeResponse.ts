export class RecipeResponse {
  id: number  = 0;
  name: string = "";
  dateOfCreation: Date = new Date();
  info: string = "";
  parentId: number = 0;
  previousId: number = 0;
}
