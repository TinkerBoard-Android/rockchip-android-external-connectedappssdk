/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.enterprise.connectedapps.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotate the connector which manages connections between processes. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomProfileConnector {

  /** A type of profile supported by the SDK. */
  enum ProfileType {
    UNKNOWN,
    NONE,
    WORK,
    PERSONAL;
  }

  /**
   * The service that will be generated by the SDK.
   *
   * <p>If set to empty string, this defaults to the name of the connector suffixed with _Service.
   */
  String serviceClassName() default "";

  /**
   * The "primary" profile used by {@code .primary()}, {@code .secondary()}, and {@code
   * .suppliers()} calls.
   *
   * <p>This should typically be the profile which displays a combined experience, if any.
   *
   * <p>If this is not set, or is set to {@link ProfileType#NONE}, then methods which depend on the
   * existence of a primary profile will not be accessible.
   */
  ProfileType primaryProfile() default ProfileType.NONE;

  /** Custom parcelable wrappers to be accessible to all users of this connector */
  Class<?>[] parcelableWrappers() default {};

  /** Custom future wrappers to be accessible to all users of this connector */
  Class<?>[] futureWrappers() default {};

  /**
   * Other {@link CustomProfileConnector} annotated types which we can import configuration from.
   *
   * <p>This will import {@link #parcelableWrappers()} and {@link #futureWrappers()}.
   */
  Class<?>[] imports() default {};

  /**
   * Which set of restrictions should be applied to checking availability.
   *
   * <p>By default, this will require that a user be running, unlocked, and not in quiet mode.
   */
  AvailabilityRestrictions availabilityRestrictions() default AvailabilityRestrictions.DEFAULT;
}