package com.embarcadero.javaandroid;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TJSONObject extends TJSONValue {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$embarcadero$javaandroid$JSONValueType;
    protected List<TJSONPair> Elements;

    static /* synthetic */ int[] $SWITCH_TABLE$com$embarcadero$javaandroid$JSONValueType() {
        int[] r0 = $SWITCH_TABLE$com$embarcadero$javaandroid$JSONValueType;
        if (r0 == null) {
            r0 = new int[JSONValueType.valuesCustom().length];
            try {
                r0[JSONValueType.JSONArray.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                r0[JSONValueType.JSONFalse.ordinal()] = 6;
            } catch (NoSuchFieldError e2) {
            }
            try {
                r0[JSONValueType.JSONNull.ordinal()] = 7;
            } catch (NoSuchFieldError e3) {
            }
            try {
                r0[JSONValueType.JSONNumber.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                r0[JSONValueType.JSONObject.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                r0[JSONValueType.JSONString.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                r0[JSONValueType.JSONTrue.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            $SWITCH_TABLE$com$embarcadero$javaandroid$JSONValueType = r0;
        }
        return r0;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONObject;
    }

    protected List<TJSONPair> buildElements(JSONObject o) {
        try {
            List<TJSONPair> res = new LinkedList<>();
            JSONArray keys = o.names();
            for (int i = 0; i < keys.length(); i++) {
                String pname = keys.getString(i);
                Object obj = o.get(pname);
                if (obj == JSONObject.NULL) {
                    res.add(new TJSONPair(pname, new TJSONNull()));
                } else if (obj instanceof String) {
                    res.add(new TJSONPair(pname, (String) obj));
                } else if (obj instanceof Double) {
                    res.add(new TJSONPair(pname, new TJSONNumber(((Double) obj).doubleValue())));
                } else if (obj instanceof Integer) {
                    res.add(new TJSONPair(pname, new TJSONNumber(((Integer) obj).intValue())));
                } else if (obj instanceof JSONArray) {
                    res.add(new TJSONPair(pname, new TJSONArray((JSONArray) obj)));
                } else if (obj instanceof JSONObject) {
                    res.add(new TJSONPair(pname, new TJSONObject((JSONObject) obj)));
                } else if (obj instanceof Boolean) {
                    if (((Boolean) obj).booleanValue()) {
                        res.add(new TJSONPair(pname, new TJSONTrue()));
                    } else {
                        res.add(new TJSONPair(pname, new TJSONFalse()));
                    }
                }
            }
            return res;
        } catch (JSONException e) {
            return null;
        }
    }

    protected JSONObject asJSONObject() {
        try {
            JSONObject j = new JSONObject();
            for (TJSONPair pair : this.Elements) {
                switch ($SWITCH_TABLE$com$embarcadero$javaandroid$JSONValueType()[pair.value.getJsonValueType().ordinal()]) {
                    case 1:
                        j.put(pair.name, ((TJSONObject) pair.value).asJSONObject());
                        break;
                    case 2:
                        j.put(pair.name, ((TJSONArray) pair.value).asJSONArray());
                        break;
                    case 3:
                        j.put(pair.name, ((TJSONString) pair.value).getValue());
                        break;
                    case 4:
                        j.put(pair.name, ((TJSONNumber) pair.value).getValue());
                        break;
                    case 5:
                        j.put(pair.name, true);
                        break;
                    case 6:
                        j.put(pair.name, false);
                        break;
                    case 7:
                        j.put(pair.name, JSONObject.NULL);
                        break;
                }
            }
            return j;
        } catch (JSONException e) {
            return null;
        }
    }

    public TJSONObject() {
        this.Elements = new LinkedList();
    }

    public static TJSONObject Parse(String value) {
        try {
            JSONObject o = new JSONObject(value);
            return new TJSONObject(o);
        } catch (JSONException e) {
            return null;
        }
    }

    public TJSONObject(JSONObject json) {
        this.Elements = buildElements(json);
    }

    public TJSONObject(TJSONPair pair) {
        this.Elements = new LinkedList();
        addPairs(pair);
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return asJSONObject().toString();
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return asJSONObject();
    }

    public String getString(String name) {
        TJSONPair p = get(name);
        if (p == null) {
            return null;
        }
        return ((TJSONString) p.value).getValue();
    }

    public Boolean getBoolean(String name) {
        TJSONPair p = get(name);
        if (p == null) {
            return null;
        }
        if (p.value instanceof TJSONTrue) {
            return true;
        }
        return false;
    }

    public Double getDouble(String name) {
        TJSONPair p = get(name);
        if (p == null) {
            return null;
        }
        return ((TJSONNumber) p.value).getValue();
    }

    public TJSONObject getJSONObject(String name) {
        TJSONPair p = get(name);
        if (p == null) {
            return null;
        }
        return (TJSONObject) p.value;
    }

    public Integer getInt(String name) {
        TJSONPair p = get(name);
        if (p == null) {
            return null;
        }
        return Integer.valueOf(((TJSONNumber) p.value).getValue().intValue());
    }

    public TJSONArray getJSONArray(String name) {
        TJSONPair p = get(name);
        if (p == null) {
            return null;
        }
        return (TJSONArray) p.value;
    }

    public boolean has(String name) {
        return get(name) != null;
    }

    public TJSONPair get(String name) {
        for (TJSONPair v : this.Elements) {
            if (v.name.equals(name)) {
                return v;
            }
        }
        return null;
    }

    public TJSONObject addPairs(TJSONPair pair) {
        this.Elements.add(pair);
        return this;
    }

    public TJSONObject addPairs(String name, TJSONValue value) {
        return addPairs(new TJSONPair(name, value));
    }

    public TJSONObject addPairs(String name, int value) {
        return addPairs(name, new TJSONNumber(value));
    }

    public TJSONObject addPairs(String name, String value) {
        return addPairs(name, new TJSONString(value));
    }

    public TJSONObject addPairs(String name, long value) {
        return addPairs(name, new TJSONNumber(value));
    }

    public TJSONObject addPairs(String name, double value) {
        return addPairs(name, new TJSONNumber(value));
    }

    public TJSONObject addPairs(String name, boolean value) {
        return value ? addPairs(name, new TJSONTrue()) : addPairs(name, new TJSONFalse());
    }
}
