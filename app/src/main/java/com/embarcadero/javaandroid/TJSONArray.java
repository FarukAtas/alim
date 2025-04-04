package com.embarcadero.javaandroid;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class TJSONArray extends TJSONValue {
    protected List<TJSONValue> Elements;

    public static TJSONArray Parse(String JSONString) throws DBXException {
        try {
            return new TJSONArray(new JSONArray(JSONString));
        } catch (JSONException e) {
            throw new DBXException(e.getMessage());
        }
    }

    public TJSONArray(List<TJSONValue> JSONValues) throws JSONException {
        this.Elements = JSONValues;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return asJSONArray().toString();
    }

    public TJSONArray() {
        this.Elements = new LinkedList();
    }

    public TJSONArray(JSONArray json) {
        this.Elements = buildElements(json);
    }

    protected List<TJSONValue> buildElements(JSONArray arr) {
        try {
            List<TJSONValue> res = new LinkedList<>();
            for (int i = 0; i < arr.length(); i++) {
                Object obj = arr.get(i);
                if (obj == JSONObject.NULL) {
                    res.add(new TJSONNull());
                } else if (obj instanceof String) {
                    res.add(new TJSONString((String) obj));
                } else if (obj instanceof Double) {
                    res.add(new TJSONNumber(((Double) obj).doubleValue()));
                } else if (obj instanceof Integer) {
                    res.add(new TJSONNumber(((Integer) obj).intValue()));
                } else if (obj instanceof Long) {
                    res.add(new TJSONNumber(((Long) obj).longValue()));
                } else if (obj instanceof JSONArray) {
                    res.add(new TJSONArray((JSONArray) obj));
                } else if (obj instanceof JSONObject) {
                    res.add(new TJSONObject((JSONObject) obj));
                } else if (obj instanceof Boolean) {
                    if (((Boolean) obj).booleanValue()) {
                        res.add(new TJSONTrue());
                    } else {
                        res.add(new TJSONFalse());
                    }
                }
            }
            return res;
        } catch (JSONException e) {
            return null;
        }
    }

    public TJSONArray add(int value) {
        JSONArray app = (JSONArray) getInternalObject();
        app.put(value);
        this.Elements = buildElements(app);
        return this;
    }

    public TJSONArray add(long value) {
        JSONArray app = (JSONArray) getInternalObject();
        app.put(value);
        this.Elements = buildElements(app);
        return this;
    }

    public TJSONArray add(boolean value) {
        JSONArray app = (JSONArray) getInternalObject();
        app.put(value);
        this.Elements = buildElements(app);
        return this;
    }

    public TJSONArray add(double value) throws DBXException {
        JSONArray app = (JSONArray) getInternalObject();
        try {
            app.put(value);
            this.Elements = buildElements(app);
            return this;
        } catch (JSONException e) {
            throw new DBXException(e.getMessage());
        }
    }

    public TJSONArray add(String value) {
        JSONArray app = (JSONArray) getInternalObject();
        app.put(value);
        this.Elements = buildElements(app);
        return this;
    }

    public TJSONArray add(Object value) {
        JSONArray app = (JSONArray) getInternalObject();
        app.put(value);
        this.Elements = buildElements(app);
        return this;
    }

    protected JSONArray asJSONArray() {
        JSONArray arr = new JSONArray();
        for (TJSONValue v : this.Elements) {
            arr.put(v.getInternalObject());
        }
        return arr;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return asJSONArray();
    }

    public TJSONArray add(TJSONValue value) {
        this.Elements.add(value);
        return this;
    }

    public String getString(int index) {
        TJSONValue p = get(index);
        if (p == null) {
            return null;
        }
        return ((TJSONString) p).getValue();
    }

    public Double getDouble(int index) {
        TJSONValue p = get(index);
        if (p == null) {
            return null;
        }
        return ((TJSONNumber) p).getValue();
    }

    public TJSONObject getJSONObject(int index) {
        TJSONValue p = get(index);
        if (p == null) {
            return null;
        }
        return (TJSONObject) p;
    }

    public Integer getInt(int index) {
        return Integer.valueOf(getDouble(index).intValue());
    }

    public Boolean getBoolean(int index) {
        TJSONValue p = get(index);
        if (p == null) {
            return null;
        }
        if (p instanceof TJSONTrue) {
            return true;
        }
        return false;
    }

    public TJSONArray getJSONArray(int index) {
        TJSONValue p = get(index);
        if (p == null) {
            return null;
        }
        return (TJSONArray) p;
    }

    public TJSONValue get(int index) {
        return this.Elements.get(index);
    }

    public TJSONString getAsJsonString(int index) {
        return (TJSONString) get(index);
    }

    public TJSONObject getAsJsonObject(int index) {
        return (TJSONObject) get(index);
    }

    public TJSONArray getAsJsonArray(int index) {
        return (TJSONArray) get(index);
    }

    public TJSONArray remove(int index) {
        this.Elements.remove(index);
        return this;
    }

    public long size() {
        return this.Elements.size();
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONArray;
    }
}
