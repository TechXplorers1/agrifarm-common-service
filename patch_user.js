const fs = require('fs');
const path = require('path');

const userFile = 'c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\entity\\User.java';
let content = fs.readFileSync(userFile, 'utf8');
if (!content.includes('private String houseNo;')) {
    content = content.replace('private String village;', 'private String village;\n\n    @Column(name = "house_no")\n    private String houseNo;\n\n    @Column(name = "street")\n    private String street;\n\n    @Column(name = "state")\n    private String state;\n\n    @Column(name = "country")\n    private String country;\n\n    @Column(name = "pincode")\n    private String pincode;');
    fs.writeFileSync(userFile, content);
}

const userDtoFile = 'c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\UserDTO.java';
let contentDto = fs.readFileSync(userDtoFile, 'utf8');
if (!contentDto.includes('private String houseNo;')) {
    contentDto = contentDto.replace('private String village;', 'private String village;\n    private String houseNo;\n    private String street;\n    private String state;\n    private String country;\n    private String pincode;');
    fs.writeFileSync(userDtoFile, contentDto);
}

console.log('Updated User.java and UserDTO.java');
