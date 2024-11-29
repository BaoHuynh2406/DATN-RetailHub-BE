package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.response.BarcodeLookUp.InfoProduct;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.BarcodeLookUpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BarcodeLookUpServiceImpl implements BarcodeLookUpService {

    @Override
    public InfoProduct getInfoProduct(String barcode) {
        String baseUrl = "https://muaday.vn";
        String urlString = baseUrl + "/c/" + barcode;
        InfoProduct infoProduct = new InfoProduct();

        try {
            // Tạo URL object
            URL url = new URL(urlString);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // Thành công
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Lấy thông tin tên sản phẩm, giá và URL hình ảnh
                    String productName = extractProductName(response.toString());
                    //trường hợp không tìm thấy
                    if (productName == null) {
                        throw new AppException(ErrorCode.BARCODE_NOT_FOUND);
                    }

                    infoProduct.setProductName(productName);
                    infoProduct.setPrice(extractProductPrice(response.toString()));
                    infoProduct.setImageUrl(baseUrl + extractImageUrl(response.toString()));

                    return infoProduct;

                }
            } else {
                throw new AppException(ErrorCode.BARCODE_NOT_FOUND);
            }

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting the barcode", e);
        }
    }

    // Hàm trích xuất tên sản phẩm
    private String extractProductName(String htmlContent) {
        Pattern pattern = Pattern.compile("<h3 class=\"item-name\"[^>]*>(.*?)</h3>");
        Matcher matcher = pattern.matcher(htmlContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // Hàm trích xuất giá sản phẩm và chuyển thành kiểu double
    private Double extractProductPrice(String htmlContent) {
        Pattern pattern = Pattern.compile("<div class=\"item-price\"[^>]*>(.*?)</div>");
        Matcher matcher = pattern.matcher(htmlContent);
        if (matcher.find()) {
            // Lấy giá trị, loại bỏ các ký tự không phải số (ví dụ: dấu chấm, khoảng trắng, dấu phẩy)
            String priceString = matcher.group(1).trim().replaceAll("[^\\d]", "");

            // Chuyển đổi giá trị thành kiểu double
            try {
                return Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Hàm trích xuất URL hình ảnh
    private String extractImageUrl(String htmlContent) {
        Pattern pattern = Pattern.compile("<a class=\"image-popup-link\"[^>]*href=\"(.*?)\"");
        Matcher matcher = pattern.matcher(htmlContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
