package br.com.caelum.carangobom.domain.entity.form;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class PageableDummy implements Pageable {
    int pageNumber;
    int pagesSize;
    Sort sort;

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pagesSize;
    }

    @Override
    public long getOffset() {
        return pageNumber*pagesSize;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new PageableDummy(this.pageNumber+1, this.pagesSize, this.sort);
    }

    @Override
    public Pageable previousOrFirst() {
        if(this.hasPrevious()){
            return new PageableDummy(this.pageNumber-1,this.pagesSize, this.sort);
        }
        return new PageableDummy(1,this.pagesSize, this.sort);
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return pageNumber>1;
    }
}
