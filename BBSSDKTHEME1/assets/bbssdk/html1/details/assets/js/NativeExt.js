var $pluginID = "com.mob.BBSSDK";

/**
 * 流水号
 * @type {number}
 * @private
 */
var _seqId = 0;

$mob = function () {};

/**
 * 扩展类
 */
$mob.ext = function () {};

/**
 * 回调方法集合
 * @type {{}}
 * @private
 */
$mob.ext._callbackFuncs = {};

/**
 * 绑定回调方法
 * @param callback      回调方法
 * @returns {string}    回调方法描述
 * @private
 */
$mob.ext._bindCallbackFunc = function (callback) {
    var sessionId = new Date().getTime() + _seqId;
    _seqId ++;

    $mob.ext._callbackFuncs [sessionId] = function (data) {
        if (callback !== null) {
            callback(data);
        }
        delete  $mob.ext._callbackFuncs [sessionId];
        $mob.ext._callbackFuncs [sessionId] = null;
    };

    return "$mob.ext._callbackFuncs[" + sessionId + "]";
};

