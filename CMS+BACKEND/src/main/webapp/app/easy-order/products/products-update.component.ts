import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductsService } from './products.service';
import { IExtra } from 'app/shared/model/extra.model';
import { ExtraService } from 'app/entities/extra';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from 'app/entities/discount';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { IListOfValue, ListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from 'app/entities/list-of-value';
import { AccountService } from 'app/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { finalize } from 'rxjs/operators';
import { ExtrasService } from 'app/easy-order/extras';
import { IOrderHistory } from 'app/shared/model/order-history.model';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { TopService } from 'app/entities/top';
import { ITop, Top } from 'app/shared/model/top.model';
@Component({
    selector: 'jhi-products-update',
    templateUrl: './products-update.component.html',
    styleUrls: ['../easy-order.scss']
})
export class ProductsUpdateComponent implements OnInit {
    product: IProduct;
    isSaving: boolean;

    uploadPercent: Observable<number>;

    extras: IExtra[];
    products: IProduct[];
    combos: IProduct[];
    discounts: IDiscount[];
    categories: ICategory[];
    client: IClient;
    topProducts: IProduct[];
    isTopProduct = false;
    listTypesProduct: IListOfValue[];
    top: ITop;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productService: ProductsService,
        protected topService: TopService,
        protected discountService: DiscountService,
        protected categoryService: CategoryService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute,
        protected listofvalueService: ListOfValueService,
        protected accountService: AccountService,
        protected extrasService: ExtrasService,
        private storage: AngularFireStorage
    ) {
        this.client = new class implements IClient {
            categories: ICategory[];
            discounts: IDiscount[];
            email: string;
            extras: IExtra[];
            id: number;
            name: string;
            orderHistories: IOrderHistory[];
            orders: IOrderOk[];
            products: IProduct[];
            userId: number;
            topProducts: IProduct[];
        }();
        this.top = new class implements ITop {
            id: number;
            productsId: number;
            productsName: string;
            quantity: number;
            type: string;
        }();
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
        });
        this.accountService.identity().then(account => {
            this.getClient(account.id, client => {
                this.client = client;
                this.load();
            });
        });
    }
    load() {
        this.extrasService
            .findExtrasByClient(this.client.id)
            .pipe(
                filter((mayBeOk: HttpResponse<IExtra[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExtra[]>) => response.body)
            )
            .subscribe(
                (res: IExtra[]) => (this.extras = res.filter(response => response.status === true)),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.productService
            .getProductsByClient(this.client.id)
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe(
                (res: IProduct[]) => (this.products = res.filter(value => value.type === 'P')),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.discountService
            .findDiscountByUser(this.client.id)
            .pipe(
                filter((mayBeOk: HttpResponse<IDiscount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDiscount[]>) => response.body)
            )
            .subscribe((res: IDiscount[]) => (this.discounts = res), (res: HttpErrorResponse) => this.onError(res.message));

        this.categoryService
            .getCategoriesByClient(this.client.id)
            .pipe(
                filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategory[]>) => response.body)
            )
            .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));

        this.getListTop();
        this.getListTypeProducts();
    }
    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            if (this.isTopProduct) {
                this.createTop();
                this.topService.create(this.top);
            } else {
                this.subscribeToSaveResponseT(this.topService.delete(this.top.id));
            }
            this.subscribeToSaveResponse(this.productService.update(this.product));
        } else {
            this.product.clientId = this.client.id;
            if (this.product.type === 'P') {
                this.product.combos = null;
            }
            this.product.status = true;
            this.subscribeToSaveResponse(this.productService.create(this.product));
        }
    }
    protected subscribeToSaveResponseT(result: Observable<HttpResponse<ITop>>) {
        result.subscribe((res: HttpResponse<ITop>) => this.onSaveSuccessT(), (res: HttpErrorResponse) => this.onSaveErrorT());
    }
    protected onSaveSuccessT() {
        this.isSaving = false;
    }

    protected onSaveErrorT() {
        this.isSaving = false;
    }
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
        result.subscribe(
            (res: HttpResponse<IProduct>) => {
                this.product.id = res.body.id;
                if (this.isTopProduct) {
                    this.createTop();
                    this.subscribeToSaveResponseT(this.topService.create(this.top));
                }
                this.onSaveSuccess();
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackExtraById(index: number, item: IExtra) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackDiscountById(index: number, item: IDiscount) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackClientById(index: number, item: IClient) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    getListTypeProducts() {
        this.listofvalueService.getLstProducts().subscribe((types: HttpResponse<ListOfValue[]>) => {
            this.listTypesProduct = types.body;
            this.product.type = 'P';
        });
    }
    getTypeProduct(product: IProduct) {
        return this.listTypesProduct.find(type => type.value === product.type).description;
    }
    setTypeProduct(Type) {
        this.product.type = Type;
    }
    getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }
    uploadImage(event) {
        const file = event.target.files[0];
        const filePath = `easy-order${new Date().getTime()}_${file.name}`;
        const fileRef = this.storage.ref(filePath);
        const task = this.storage.upload(filePath, file);

        // observe percentage changes
        this.uploadPercent = task.percentageChanges();
        // get notified when the download URL is available
        task.snapshotChanges()
            .pipe(
                finalize(() => {
                    fileRef.getDownloadURL().subscribe(value => (this.product.urlImage = value));
                })
            )
            .subscribe();
    }
    getTypeValueProduct() {
        return this.product.type === 'C';
    }
    getListTop() {
        this.productService.getProductsTop(this.client.id).subscribe((response: HttpResponse<Product[]>) => {
            this.topProducts = response.body;
            this.isTopProduct = this.topProducts.find(top => top.id === this.product.id) ? true : false;
            if (this.isTopProduct) {
                this.getTop();
            }
            // this.isTopProduct ? this.getTop() : null;
        });
    }
    setTop(isTopProduct: boolean) {
        this.isTopProduct = isTopProduct;
    }
    getTop() {
        this.topService
            .getTopByIdProduct(this.product.id)
            .pipe(
                filter((mayBeOk: HttpResponse<ITop>) => mayBeOk.ok),
                map((response: HttpResponse<ITop>) => response.body)
            )
            .subscribe((res: ITop) => (this.top = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    createTop() {
        console.log(this.product.type);
        this.top.type = this.product.type;
        this.top.productsId = this.product.id;
        this.top.quantity = 1;
        // this.top = new Top(null, 1, this.product.type, null,
        //     this.product.id);
    }
}
