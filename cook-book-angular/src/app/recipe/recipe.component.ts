import {Component, OnInit} from '@angular/core';
import {RecipeResponse} from "../model/RecipeResponse";
import {environment} from "../../environment/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {RecipeRequestRoot} from "../model/RecipeRequestRoot";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {
  recipes: RecipeResponse[] = [];
  historyRecipes: RecipeResponse[] = [];
  postRecipeRoot: RecipeRequestRoot = new RecipeRequestRoot();
  updateRecipeRoot: RecipeRequestRoot = new RecipeRequestRoot();
  messageToUser = "";
  showAddNewRipe: boolean = false;
  showUpdateNewRipe: boolean = false;
  showHistory: boolean = false;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  ngOnInit() {
    this.getAllRecipes()
  }

  getAllRecipes() {
    this.http.get<RecipeResponse[]>(environment.backendURL + "/recipes").subscribe({
      next: ((response: RecipeResponse[]) => {
        this.recipes = response;
      }),
      error: (error => {
        this.messageToUser = "Something went wrong. Try again"
      })
    })
  }

  addRecipeRoot(): void {
    if (this.checkInput(this.postRecipeRoot)) {
      return;
    }
    const body = JSON.stringify(this.postRecipeRoot);
    this.http.post<RecipeRequestRoot>(environment.backendURL + "/recipes", body, {
      headers: {
        "Content-Type": "application/json"
      }
    }).subscribe({
      next: ((response: RecipeRequestRoot) => {
        this.postRecipeRoot = response;
        console.log(this.postRecipeRoot);
        this.clearForm();
        this.getAllRecipes();
        this.messageToUser = "You successful add a new recipe!"
        this.showAddNewRipe = false;
      }),
      error: (error => {
        this.messageToUser = "Something went wrong. Try again"
      })
    })
  }

  goUpdate() {
    if (this.checkInput(this.updateRecipeRoot)) {
      return;
    }
    const body = JSON.stringify(this.updateRecipeRoot);
    this.http.post<RecipeRequestRoot>(environment.backendURL + "/recipes/update/" + this.updateRecipeRoot.id, body, {
      headers: {
        "Content-Type": "application/json"
      }
    }).subscribe({
      next: ((response: RecipeRequestRoot) => {
        this.postRecipeRoot = response;
        console.log(this.postRecipeRoot);
        this.clearForm();
        this.getAllRecipes();
        this.messageToUser = "You successful update a recipe!"
        this.showUpdateNewRipe = false;
      }),
      error: (error => {
        this.messageToUser = "Something went wrong. Try again"
      })
    })
  }

  getHistory(parentId: number) {
    if (parentId == null) {
      return;
    }
    this.showHistory = !this.showHistory;
    this.http.get<RecipeResponse[]>(environment.backendURL + "/recipes/history/" + parentId).subscribe({
      next: ((response: RecipeResponse[]) => {
        this.historyRecipes = response;
      }),
      error: (error => {
        this.messageToUser = "Something went wrong ";
      })
    })
  }

  checkInput(recipe: RecipeRequestRoot): boolean {
    if (recipe.name.length < 5 || recipe.info.length < 15) {
      this.messageToUser = "Name should be longer then 5 characters, info longer then 15 character!";
      return true;
    }
    return false;
  }

  clearForm() {
    this.postRecipeRoot = new RecipeRequestRoot();
  }

  setUpdateRecipe(recipe: RecipeResponse) {
    this.updateRecipeRoot = recipe;
    this.showUpdateNewRipe = !this.showUpdateNewRipe;
  }

  toInfoPage(parentId: number) {
    this.router.navigate(['/info'], {queryParams: {parentId: parentId}});
  }

  showAddNewRecipeBlock() {
    this.showAddNewRipe = !this.showAddNewRipe;
  }
}
