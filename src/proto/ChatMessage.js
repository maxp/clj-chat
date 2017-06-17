/*eslint-disable block-scoped-var, no-redeclare, no-control-regex, no-prototype-builtins*/
(function($protobuf) {
    "use strict";

    // Common aliases
    var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;
    
    // Exported root namespace
    var $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});
    
    $root.ChatMessage = (function() {
    
        /**
         * Properties of a ChatMessage.
         * @exports IChatMessage
         * @interface IChatMessage
         * @property {string} nick ChatMessage nick
         * @property {string} text ChatMessage text
         */
    
        /**
         * Constructs a new ChatMessage.
         * @exports ChatMessage
         * @classdesc Represents a ChatMessage.
         * @constructor
         * @param {IChatMessage=} [properties] Properties to set
         */
        function ChatMessage(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }
    
        /**
         * ChatMessage nick.
         * @member {string}nick
         * @memberof ChatMessage
         * @instance
         */
        ChatMessage.prototype.nick = "";
    
        /**
         * ChatMessage text.
         * @member {string}text
         * @memberof ChatMessage
         * @instance
         */
        ChatMessage.prototype.text = "";
    
        /**
         * Creates a new ChatMessage instance using the specified properties.
         * @function create
         * @memberof ChatMessage
         * @static
         * @param {IChatMessage=} [properties] Properties to set
         * @returns {ChatMessage} ChatMessage instance
         */
        ChatMessage.create = function create(properties) {
            return new ChatMessage(properties);
        };
    
        /**
         * Encodes the specified ChatMessage message. Does not implicitly {@link ChatMessage.verify|verify} messages.
         * @function encode
         * @memberof ChatMessage
         * @static
         * @param {IChatMessage} message ChatMessage message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        ChatMessage.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            writer.uint32(/* id 1, wireType 2 =*/10).string(message.nick);
            writer.uint32(/* id 2, wireType 2 =*/18).string(message.text);
            return writer;
        };
    
        /**
         * Encodes the specified ChatMessage message, length delimited. Does not implicitly {@link ChatMessage.verify|verify} messages.
         * @function encodeDelimited
         * @memberof ChatMessage
         * @static
         * @param {IChatMessage} message ChatMessage message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        ChatMessage.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };
    
        /**
         * Decodes a ChatMessage message from the specified reader or buffer.
         * @function decode
         * @memberof ChatMessage
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {ChatMessage} ChatMessage
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        ChatMessage.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.ChatMessage();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.nick = reader.string();
                    break;
                case 2:
                    message.text = reader.string();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            if (!message.hasOwnProperty("nick"))
                throw $util.ProtocolError("missing required 'nick'", { instance: message });
            if (!message.hasOwnProperty("text"))
                throw $util.ProtocolError("missing required 'text'", { instance: message });
            return message;
        };
    
        /**
         * Decodes a ChatMessage message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof ChatMessage
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {ChatMessage} ChatMessage
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        ChatMessage.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };
    
        /**
         * Verifies a ChatMessage message.
         * @function verify
         * @memberof ChatMessage
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        ChatMessage.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (!$util.isString(message.nick))
                return "nick: string expected";
            if (!$util.isString(message.text))
                return "text: string expected";
            return null;
        };
    
        /**
         * Creates a ChatMessage message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof ChatMessage
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {ChatMessage} ChatMessage
         */
        ChatMessage.fromObject = function fromObject(object) {
            if (object instanceof $root.ChatMessage)
                return object;
            var message = new $root.ChatMessage();
            if (object.nick != null)
                message.nick = String(object.nick);
            if (object.text != null)
                message.text = String(object.text);
            return message;
        };
    
        /**
         * Creates a plain object from a ChatMessage message. Also converts values to other types if specified.
         * @function toObject
         * @memberof ChatMessage
         * @static
         * @param {ChatMessage} message ChatMessage
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        ChatMessage.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.nick = "";
                object.text = "";
            }
            if (message.nick != null && message.hasOwnProperty("nick"))
                object.nick = message.nick;
            if (message.text != null && message.hasOwnProperty("text"))
                object.text = message.text;
            return object;
        };
    
        /**
         * Converts this ChatMessage to JSON.
         * @function toJSON
         * @memberof ChatMessage
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        ChatMessage.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };
    
        return ChatMessage;
    })();

    return $root;
})(protobuf);
