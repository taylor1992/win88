// 店铺类型映射工具
function getShopTypeName(type) {
    switch (type) {
        case 1: return 'Tiktok';
        case 2: return 'Shopee';
        case 3: return 'Lazada';
        case 4: return 'Facebook';
        case 5: return 'Other';
        default: return '-';
    }
}

function getEnable(type) {
    switch (type) {
        case 0: return 'Disable';
        case 1: return 'Enable';
        default: return '-';
    }
}

function getProductStatus(type) {
    switch (type) {
        case 0: return 'Offline';
        case 1: return 'Online';
        case 2: return 'Draft';
        default: return '-';
    }
}