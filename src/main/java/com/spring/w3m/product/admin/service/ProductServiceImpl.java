package com.spring.w3m.product.admin.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.w3m.paging.common.Pagination;
import com.spring.w3m.paging.common.Search;
import com.spring.w3m.product.admin.dao.ProductDAO;
import com.spring.w3m.product.admin.vo.ProductVO;
import com.spring.w3m.upload.common.AwsS3;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO dao;
	public AwsS3 awsS3 = AwsS3.getInstance();

	@Override
	public List<ProductVO> getProductList(ProductVO vo) {
		return dao.getProductList(vo);
	}

	@Override
	public ProductVO getProduct(ProductVO vo) {
		return dao.getProduct(vo);
	}

	@Override
	public void deleteProduct(ProductVO vo) {
		dao.deleteProduct(vo);
	}

	@Override
	public void insertProduct(ProductVO vo, MultipartFile prod_thumb, MultipartFile image1, MultipartFile image2,
			MultipartFile image3, MultipartFile image4, MultipartFile image5, MultipartFile image6,
			MultipartFile image7, MultipartFile image8, MultipartFile image9, MultipartFile image10)
			throws IOException {

		vo.setProd_title_image(singleUpload(prod_thumb));
		vo.setProd_image1(singleUpload(image1));
		vo.setProd_image2(singleUpload(image2));
		vo.setProd_image3(singleUpload(image3));
		vo.setProd_image4(singleUpload(image4));
		vo.setProd_image5(singleUpload(image5));
		vo.setProd_image6(singleUpload(image6));
		vo.setProd_image7(singleUpload(image7));
		vo.setProd_image8(singleUpload(image8));
		vo.setProd_image9(singleUpload(image9));
		vo.setProd_image10(singleUpload(image10));

		floorPrice(vo); // 1의 자리 0으로 내림하는 작업
		String prod_code = vo.getProd_code(); // 상품코드 받아서 카테고리분류하는 작업
		sortCategory(vo, prod_code);
		dao.insertProduct(vo);
	}

	@Override
	public void updateProduct(ProductVO vo, MultipartFile prod_thumb, MultipartFile image1, MultipartFile image2,
			MultipartFile image3, MultipartFile image4, MultipartFile image5, MultipartFile image6,
			MultipartFile image7, MultipartFile image8, MultipartFile image9, MultipartFile image10)
			throws IOException {

		vo.setProd_title_image(singleUpload(prod_thumb));
		vo.setProd_image1(singleUpload(image1));
		vo.setProd_image2(singleUpload(image2));
		vo.setProd_image3(singleUpload(image3));
		vo.setProd_image4(singleUpload(image4));
		vo.setProd_image5(singleUpload(image5));
		vo.setProd_image6(singleUpload(image6));
		vo.setProd_image7(singleUpload(image7));
		vo.setProd_image8(singleUpload(image8));
		vo.setProd_image9(singleUpload(image9));
		vo.setProd_image10(singleUpload(image10));

		floorPrice(vo); // 1의 자리 0으로 내림하는 작업
		String prod_code = vo.getProd_code(); // 상품코드 받아서 카테고리분류하는 작업
		sortCategory(vo, prod_code);
		dao.updateProduct(vo);
	}

	public String singleUpload(MultipartFile file) throws IOException {
		if (file != null) {
			InputStream ism = file.getInputStream();
			String fileName = file.getOriginalFilename();
			String contentType = file.getContentType();
			long contentLength = file.getSize();

			awsS3.uploadProduct(ism, fileName, contentType, contentLength); // 상품이미지 업로드
			if (fileName == "") {
				return "https://imageup.s3.ap-northeast-2.amazonaws.com/product/";
			} else {
				return "https://imageup.s3.ap-northeast-2.amazonaws.com/product/" + fileName;
			}
		} else {
			return "https://imageup.s3.ap-northeast-2.amazonaws.com/product/";
		}
	}

	public void sortCategory(ProductVO vo, String prod_code) { // 카테고리분류작업
		String[] category = prod_code.split("-");
		try {
			vo.setProd_category1(category[0]); // MM
			vo.setProd_category2(category[1]); // P
			vo.setProd_category3(category[2]); // L
			vo.setProd_category4(category[3]); // 94
		} catch (Exception e) {
			System.err.println("상품코드를 형식에 맞게 작성해주세요!!!");
			vo.setProd_category1("temp"); // MM
			vo.setProd_category2("temp"); // P
			vo.setProd_category3("temp"); // L
			vo.setProd_category4("temp"); // 94
		}
	}

	public ProductVO floorPrice(ProductVO vo) { // 1의 자리 0으로 내림하는 메서드
		String stringSalePrice = String.valueOf(vo.getProd_price_sale());
		String stringPoint = String.valueOf(vo.getProd_point());

		Double doubleSalePrice = Double.parseDouble(stringSalePrice);
		Double doublePoint = Double.parseDouble(stringPoint);

		int salePrice = (int) ((Math.floor(doubleSalePrice * 0.1)) * 10);
		int point = (int) ((Math.floor(doublePoint * 0.1)) * 10);

		vo.setProd_price_sale(salePrice);
		vo.setProd_point(point);

		return vo;
	}

	@Override // 재고추가하기
	public void addStock(ProductVO vo) {
		int stock = vo.getAddStock() + vo.getProd_amount();
		if (stock < 0) {
			System.out.println("재고는 음수가 될수 없으므로 0으로 설정합니다.");
			vo.setProd_amount(0);
		} else {
			vo.setProd_amount(stock);
		}
		dao.addStock(vo);
	}

	@Override
	public int getProductListCnt(Search search) {
		return dao.getProductListCnt(search);
	}

	@Override
	public List<ProductVO> getPageList(Search search) {
		return dao.getPageList(search);
	}

	@Override
	public int getSearchCnt(String searchKeyword) {
		return dao.getSearchCnt(searchKeyword);
	}

	@Override
	public List<ProductVO> getSearchPagingList(Pagination pagination) {
		return dao.getSearchPagingList(pagination);
	}

}
