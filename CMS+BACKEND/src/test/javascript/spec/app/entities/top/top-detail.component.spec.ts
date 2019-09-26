/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { TopDetailComponent } from 'app/entities/top/top-detail.component';
import { Top } from 'app/shared/model/top.model';

describe('Component Tests', () => {
    describe('Top Management Detail Component', () => {
        let comp: TopDetailComponent;
        let fixture: ComponentFixture<TopDetailComponent>;
        const route = ({ data: of({ top: new Top(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [TopDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TopDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TopDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.top).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
