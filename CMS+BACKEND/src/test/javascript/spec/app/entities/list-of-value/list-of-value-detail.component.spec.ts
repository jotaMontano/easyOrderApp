/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ListOfValueDetailComponent } from 'app/entities/list-of-value/list-of-value-detail.component';
import { ListOfValue } from 'app/shared/model/list-of-value.model';

describe('Component Tests', () => {
    describe('ListOfValue Management Detail Component', () => {
        let comp: ListOfValueDetailComponent;
        let fixture: ComponentFixture<ListOfValueDetailComponent>;
        const route = ({ data: of({ listOfValue: new ListOfValue(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ListOfValueDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ListOfValueDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ListOfValueDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.listOfValue).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
