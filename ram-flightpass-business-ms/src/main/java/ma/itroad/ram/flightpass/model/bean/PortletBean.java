
package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.itroad.ram.flightpass.model.enums.PageName;
import ma.itroad.ram.flightpass.model.enums.PortletPosition;


import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortletBean  {
    private Long Id ;
    private PortletPosition position;
    private String imagePath;
    private String message;
    private String title;
    private String description;
    private PageName pageName;
    private Long imageDisplayingOrder;
    private Long p_segmentRef_Id;


}

